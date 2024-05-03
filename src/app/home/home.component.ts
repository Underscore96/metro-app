import { Component, OnInit } from '@angular/core';
import { AdministComponent } from '../administ/administ.component';
import { HeaderComponent } from "../header/header.component";
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import { TimebarComponent } from "../timebar/timebar.component";
import * as d3 from 'd3';
import { MatCardModule } from '@angular/material/card';
import moment from 'moment';


@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    imports: [AdministComponent, HeaderComponent, CommonModule, FormsModule, MatButtonModule, MatDividerModule, MatIconModule, TimebarComponent, MatCardModule]
})
export class HomeComponent implements OnInit{

  searchTerm: string = '';
  fermate: any[] = [];
  isTableOpen: boolean = false;
  lastFetchTimestamp: any;
  direction: 'forward' | 'backward' | null = null; 

  

  constructor(private http: HttpClient) {}



  ngOnInit() {
    this.getFermate();
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
  


getDirection(): 'forward' | 'backward' | null {
  if (this.searchTerm.trim() !== '') {
    const searchTermNum = parseInt(this.searchTerm);
    return searchTermNum >= 1 && searchTermNum <= 8 ? 'forward' : 'backward';
  }
  return null;
}

}

