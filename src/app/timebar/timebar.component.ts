
import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
  selector: 'app-timebar',
  standalone: true,
  imports: [],
  templateUrl: './timebar.component.html',
  styleUrl: './timebar.component.scss'
})
export class TimebarComponent implements OnInit{
@Input() direction!: 'forward' | 'backward' | null;
fermataData: any[] = [];


  constructor(private http: HttpClient) {}
  

  ngOnInit() {
    this.fetchFermataData();
  }

  fetchFermataData() {
    this.http.get<any[]>('http://localhost:8080/Metro/rest/fermata/tutte').subscribe(data => {
      this.fermataData = data;
      this.renderVisualization();
    });
  }

  
  renderVisualization() {
    console.log('Direction:', this.direction)
    const svg = d3.select('svg');

    const margin = { top: 50, right: 20, bottom: 50, left: 20 };
    const width = +svg.attr('width') - margin.left - margin.right;
    const height = +svg.attr('height') - margin.top - margin.bottom;

    const g = svg.append('g').attr('transform', `translate(${margin.left},${margin.top})`);

    let filteredFermataData = this.fermataData;
    if (this.direction === 'forward') {
      filteredFermataData = this.fermataData.slice(0, 8); // Display first 8 elements for forward direction
    } else if (this.direction === 'backward') {
      filteredFermataData = this.fermataData.slice(this.fermataData.length - 8); // Display last 8 elements for backward direction
    }

    const x = d3.scaleLinear()
      .domain([0, filteredFermataData.length - 1])
      .range([0, width]);

    const y = height / 2;

    const line = d3.line<any>()
      .x((d, i) => x(i))
      .y(y);
      
    const path = g.append('path')
      .datum(filteredFermataData)
      .attr('class', 'line')
      .attr('d', line)
      .style('fill', 'none')
      .style('stroke', 'steelblue');

    const dots = g.selectAll('.dot')
      .data(filteredFermataData)
      .enter().append('g')
      .attr('class', 'dot')
      .attr('transform', (d, i) => `translate(${x(i)}, ${y})`);

    dots.append('circle')
      .attr('cx', 0)
      .attr('cy', 0)
      .attr('r', 5)
      .style('fill', d => d.posMezzo === 'presente' ? 'yellow' : 'steelblue');

    dots.append('text')
      .text(d => d.nome)
      .attr('x', 8)
      .attr('y', -8)
      .attr('text-anchor', 'start')
      .style('font-size', '12px')
      .style('font-weight', 'bold');

   
      dots.style('display', (d, i): string | null => {
        if (this.direction === 'forward') {
          return i < 8 ? 'block' : 'none'; // Display first 8 stops for forward direction
        } else if (this.direction === 'backward') {
          return i >= filteredFermataData.length - 8 ? 'block' : 'none'; // Display last 8 stops for backward direction
        }
        return null; // Default return value
      });

    dots.each((d, i, nodes) => {
      if (i < nodes.length - 1) {
        const nextXCoord = x(i + 1);
        const arrowX = nextXCoord - ((nextXCoord - x(i)) / 2);
        let arrow;
        if (this.direction === 'forward') {
          arrow = g.append('path')
            .attr('d', `M${arrowX},${y - 5} L${arrowX - 5},${y} L${arrowX},${y + 5} Z`)
            .style('fill', 'black');
        } else if (this.direction === 'backward') {
          arrow = g.append('path')
          .attr('d', `M${arrowX},${y - 5} L${arrowX - 5},${y} L${arrowX},${y + 5} Z`)
            .style('fill', 'black');
        }
      }
    });
  }
}

