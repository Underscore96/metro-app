import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalStorageService } from 'angular-web-storage';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  isAuthenticated: boolean = false;
  isLoggedInFlag!: boolean;
  constructor(private router: Router,  private localStorage: LocalStorageService) { }



  canActivate(): boolean {
    const loggedInUser = this.localStorage.get('loggedInUser');
    if (loggedInUser) {
      // User is authenticated, allow access to the requested route
      return true;
    } else {
      // If the user is not authenticated, redirect them to the 'login' route
      this.router.navigate(['/login']);
      return false;
    }
  }


  getLoggedInUsername(): string | null {
    const loggedInUser = this.localStorage.get('loggedInUser');
    return loggedInUser ? loggedInUser.username : null;
  }

  isLoggedIn(): boolean { // Define isLoggedIn method here
    return this.isLoggedInFlag;
  }


}
