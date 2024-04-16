import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-administ',
    standalone: true,
    templateUrl: './administ.component.html',
    styleUrl: './administ.component.scss',
    imports: [HeaderComponent, CommonModule]
})
export class AdministComponent implements OnInit {

    metroLines: any[] = [];

    constructor(private http: HttpClient) {}
   


    ngOnInit(): void {
        this.http.get<any[]>('http://localhost:8080/metro/rest/linea/leggilinea').subscribe(
          (data: any[]) => {
            this.metroLines = data;
          },
          (error) => {
            console.error('Error fetching', error);
          }
        );
      }
    }


