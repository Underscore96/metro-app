
import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import * as d3 from 'd3';

@Component({
  selector: 'app-timebar',
  standalone: true,
  imports: [],
  templateUrl: './timebar.component.html',
  styleUrl: './timebar.component.scss'
})
export class TimebarComponent implements OnInit{
@Input() direction!: 'levante' | 'ponente' | null;
@Input() searchTerm!: any;
@Output() colorMapping = new EventEmitter<{ [key: number]: string }>();
fermataData: any[] = [];




  constructor(private http: HttpClient) {}
  

  ngOnInit() {
    this.fetchFermataData();
  }

  // ngOnChanges(changes: SimpleChanges) {
  //   if (changes['searchTerm']) {
  //     this.fetchFermataData();
  //   }
  // }
  
  fetchFermataData() {
    this.http.get<any[]>('http://localhost:8080/Metro/rest/fermataFE/tutte').subscribe(data => {
      this.fermataData = data;
      this.renderTimebar()
    });
  }

  
    renderTimebar() {
      console.log('Direction:', this.direction);
      const svg = d3.select('svg');
  
      const margin = { top: 50, right: 20, bottom: 50, left: 20 };
      const width = +svg.attr('width') - margin.left - margin.right;
      const height = +svg.attr('height') - margin.top - margin.bottom;
  
      const g = svg.append('g').attr('transform', `translate(${margin.left},${margin.top})`);
      
       
        const searchTermNum = parseInt(this.searchTerm);
        const levanteStops = this.fermataData.filter(d => d.direzione.toLowerCase() === 'levante');
        const ponenteStops = this.fermataData.filter(d => d.direzione.toLowerCase() === 'ponente');
    
        let firstLineStops = [];
        let secondLineStops = [];
    
        if (searchTermNum <= 9) {
          firstLineStops = levanteStops.slice(0, 8);
          secondLineStops = levanteStops.slice(8, 15);
        } else if (searchTermNum >= 9) {
          firstLineStops = ponenteStops.slice(7, 15); 
          secondLineStops = ponenteStops.slice(0, 7); 
        }
        
    
        console.log('First Line Stops:', firstLineStops);
        console.log('Second Line Stops:', secondLineStops);
  
      // Define scales
      const x1 = d3.scaleLinear()
          .domain([0, firstLineStops.length - 1])
          .range([0, width]);
  
      const x2 = d3.scaleLinear()
          .domain([0, secondLineStops.length - 1])
          .range([width, 0]);
  
      const y1 = height / 3; 
      const y2 = (2 * height) / 3 + 50; 
  
      // Define lines
      const line1 = d3.line<any>()
          .x((d, i) => x1(i))
          .y(y1);
  
      const line2 = d3.line<any>()
          .x((d, i) => x2(i))
          .y(y2);
  
      // Draw the first line
      g.append('path')
          .datum(firstLineStops)
          .attr('class', 'line')
          .attr('d', line1)
          .style('fill', 'none')
          .style('stroke', 'steelblue');
  
      // Draw the second line
      g.append('path')
          .datum(secondLineStops)
          .attr('class', 'line')
          .attr('d', line2)
          .style('fill', 'none')
          .style('stroke', 'steelblue');
  
      // Create dots for the first and second parts
      const dots1 = g.selectAll('.dot1')
          .data(firstLineStops)
          .enter().append('g')
          .attr('class', 'dot1')
          .attr('transform', (d, i) => `translate(${x1(i)}, ${y1})`);
  
      const dots2 = g.selectAll('.dot2')
          .data(secondLineStops)
          .enter().append('g')
          .attr('class', 'dot2')
          .attr('transform', (d, i) => `translate(${x2(i)}, ${y2})`);
  
      // Define color scale
      const colorScale = d3.scaleOrdinal()
          .domain([...new Set(this.fermataData.flatMap(d => d.datiMezziFE.map((mezzo: any) => mezzo.idMezzo)))])
          .range(['#00FF00', '#FFC0CB', '#800080', '#FFA500', '#FFFF00', '#0000FF', '#808080', '#A52A2A', '#722f37', '#7FFFD4', '#ADD8E6', '#40E0D0']);
  
      const colorMapping: { [key: number]: any } = {};
      this.fermataData.flatMap(d => d.datiMezziFE).forEach((mezzo: any) => {
          colorMapping[mezzo.idMezzo] = colorScale(mezzo.idMezzo);
      });
      this.colorMapping.emit(colorMapping);
  
      // Add circles to the dots
      const addCircles = (dots: any) => {
          dots.append('circle')
              .attr('cx', 0)
              .attr('cy', 0)
              .attr('r', 5)
              .attr('fill', (d: any): any => {
                  const mezzo = d.datiMezziFE.find((mezzo: any) => mezzo.statoMezzo === 'in stazione' || mezzo.statoMezzo === 'in arrivo');
                  if (mezzo) {
                      return colorScale(mezzo.idMezzo);
                  }
                  return 'steelblue';
              });
  
          dots.append('text')
              .text((d: { nomeFermata: any; }) => d.nomeFermata)
              .attr('x', -22)
              .attr('y', -8)
              .attr('text-anchor', 'start')
              .style('font-size', '12px')
              .style('font-weight', 'bold');
      };
  
      addCircles(dots1);
      addCircles(dots2);
  
      // Add arrows for direction
      const addArrows = (dots: any, xScale: any, y: number, direction: 'forward' | 'backward') => {
          dots.each((d: any, i: number, nodes: string | any[]) => {
              if (i < nodes.length - 1) {
                  const nextXCoord = xScale(i + 1);
                  const arrowX = nextXCoord - ((nextXCoord - xScale(i)) / 2);
                  const arrowPath = direction === 'forward'
                      ? `M${arrowX},${y - 5} L${arrowX + 5},${y} L${arrowX},${y + 5} Z`
                      : `M${arrowX},${y - 5} L${arrowX - 5},${y} L${arrowX},${y + 5} Z`;
                  g.append('path')
                      .attr('d', arrowPath)
                      .style('fill', 'black');

                      
              }
              
          });

          
      };

   // Draw the dashed line between Brignole and Marassi
   const brignole = { x: x1(firstLineStops.length - 1), y: y1 };
   const marassi = { x: x2(0), y: y2 };

   g.append('line')
     .attr('x1', brignole.x)
     .attr('y1', brignole.y)
     .attr('x2', brignole.x)
     .attr('y2', marassi.y)
     .style('stroke', 'red')
     .style('stroke-width', 2)
     .style('stroke-dasharray', '5,5');
  
      addArrows(dots1, x1, y1, 'forward');
      addArrows(dots2, x2, y2, 'backward');
  
      // debug
      firstLineStops.forEach(stop => console.log('First Line Stop:', stop));
      secondLineStops.forEach(stop => console.log('Second Line Stop:', stop));
    }
  } 
