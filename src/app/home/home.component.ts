import { Component } from '@angular/core';
import { AdministComponent } from '../administ/administ.component';
import { HeaderComponent } from "../header/header.component";

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    imports: [AdministComponent, HeaderComponent]
})
export class HomeComponent {

  isTableOpen: boolean = false;



  openTable() {
    this.isTableOpen = true;
  }

  

}
