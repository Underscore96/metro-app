import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AdministComponent } from './administ/administ.component';

export const routes: Routes = [
{ path: '', redirectTo: '/home', pathMatch: 'full'},
{ path: 'home', component: HomeComponent }, 
{ path: 'admin', component: AdministComponent }, 
];
