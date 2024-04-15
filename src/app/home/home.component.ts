import { Component, OnInit } from '@angular/core';
import { AdministComponent } from '../administ/administ.component';
import { HeaderComponent } from "../header/header.component";
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';


@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    imports: [AdministComponent, HeaderComponent, CommonModule, FormsModule, MatButtonModule, MatDividerModule, MatIconModule ]
})
export class HomeComponent implements OnInit{

  searchTerm: string = '';
  fermate: any[] = [];
  isTableOpen: boolean = false;

  constructor(private http: HttpClient) {}
  ngOnInit() {
    this.getFermate();
  }





  openTable() {
    this.isTableOpen = true;
  }


   searchFermata() {
    if (this.searchTerm.trim() !== '') {
      this.http.get<any[]>('http://localhost:8080/Metro/rest/fermata/tutte')
        .subscribe(data => {
          this.fermate = data.filter(item => item.numFermata.toString() === this.searchTerm);
        });
    } else {
      this.fermate = [];
    }
  }


  getFermate(){
  this.http.get('http://localhost:8080/Metro/rest/fermata/tutte').subscribe((data: any) => {console.log(data);
},
 (error) => {console.log('error fetching data')})
  

}
}
