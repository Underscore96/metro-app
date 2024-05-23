import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import moment from 'moment';
import { AdministComponent } from '../administ/administ.component';
import { HeaderComponent } from '../header/header.component';
import { MapComponent } from '../map/map.component';
import { TimebarComponent } from '../timebar/timebar.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [AdministComponent, HeaderComponent, CommonModule, FormsModule, MatButtonModule, MatDividerModule, MatIconModule, TimebarComponent, MatCardModule, MapComponent],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss'
})
export class TableComponent implements OnInit{
  searchTerm: any;
  fermate: any[] = [];
  isTableOpen: boolean = false;
  lastFetchTimestamp: any;
  direction: 'levante' | 'ponente' | any; 
  showMap: boolean = false;
  selectedStop: any;

  

  
  ngOnInit() {
    this.getFermate();
  }

  

  constructor(private http: HttpClient) {}

  colorMapping: { [key: number]: string } = {};

  handleSelectedStop(stop: any) {
    this.selectedStop = stop;
    // Update the fermate array to include only the selected stop
    this.fermate = [stop];
    
  }

  receiveColorMapping(mapping: { [key: number]: string }) {
    this.colorMapping = mapping;
  }

  getColorForIdMezzo(idMezzo: number): string {
    return this.colorMapping[idMezzo] || 'transparent';
  }



  openTable() {
    this.isTableOpen = true;
  }


   searchFermata() {
    if (this.searchTerm.trim() !== '') {
      this.http.get<any[]>('http://localhost:8080/Metro/rest/fermataFE/tutte')
        .subscribe(data => {
          this.fermate = data.filter(fermata => fermata.numFermata.toString() === this.searchTerm);
          this.direction = this.getDirection();
        });
    } else {
      this.fermate = [];
    }
  }


  getFermate() {
    this.http.get('http://localhost:8080/Metro/rest/fermataFE/tutte').subscribe(
      (data: any) => {
        console.log(data);
        // console.log(data.map(fermata => fermata.tempi_arrivo));
        
      })
  }
  

  formatArrivalTime(arrival: string): string {
    return moment(arrival).format('LT');
}



  formatDelayTime(ritardo: string, orarioPrevisto: string): string {
    const delay = moment(ritardo).diff(moment(orarioPrevisto), 'minutes');
    return `${delay} min`;
  }
  


getDirection(): any {
  if (this.searchTerm.trim() !== '') {
    const searchTermNum = parseInt(this.searchTerm);
    return searchTermNum >= 1 && searchTermNum <= 8 ? 'levante' : 'ponente';
  }
}

isArrivalTimeGreaterThanCurrentTime(arrivalTime: string, orarioAttuale: string): boolean {
  const arrival = moment(arrivalTime);
  const currentTime = moment(orarioAttuale);
  return arrival.isBefore(currentTime);
}


}
