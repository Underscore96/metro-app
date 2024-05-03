import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AdministComponent } from './administ/administ.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './auth-guard.service';


export const routes: Routes = [
{ path: '', redirectTo: '/home', pathMatch: 'full'},
{ path: 'home', component: HomeComponent }, 
{ path: 'admin', component: AdministComponent, canActivate: [AuthGuardService]},
{ path: 'login', component: LoginComponent }, 
];
