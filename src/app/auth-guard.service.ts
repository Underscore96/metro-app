import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  isAuthenticated: boolean = false;
  constructor(private router: Router) { }



  canActivate(): boolean {
    // Check if the user is authenticated
    if (this.isAuthenticated) {
      return true; // User is authenticated, allow access to the requested route
    } else {
      // If the user is not authenticated, redirect them to the 'login' route
      this.router.navigate(['/login']);
      return this.isAuthenticated = true;
    }
  }

}
