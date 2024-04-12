import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AdministComponent } from './administ/administ.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
{ path: '', redirectTo: '/home', pathMatch: 'full'},
{ path: 'home', component: HomeComponent }, 
{ path: 'admin', component: AdministComponent }, 
{ path: 'login', component: LoginComponent }, 
];
