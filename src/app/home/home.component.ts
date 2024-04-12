import { Component } from '@angular/core';
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
export class HomeComponent {

  searchTerm: string = '';
  fermate: any[] = [];
  isTableOpen: boolean = false;

  constructor(private http: HttpClient) {}



  openTable() {
    this.isTableOpen = true;
  }


   searchFermata() {
    if (this.searchTerm.trim() !== '') {
      this.http.get<any[]>('https://6617b953ed6b8fa4348396d9.mockapi.io/fermate_metro')
        .subscribe(data => {
          this.fermate = data.filter(item => item.numero_fermata.toString() === this.searchTerm);
        });
    } else {
      this.fermate = [];
    }
  }

  

}
