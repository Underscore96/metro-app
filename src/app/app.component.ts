import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HomeComponent } from "./home/home.component";
import { AdministComponent } from './administ/administ.component';
import { HeaderComponent } from './header/header.component';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    imports: [RouterOutlet, HomeComponent, AdministComponent, HeaderComponent]
})
export class AppComponent {
  title = 'metro-app';
}
