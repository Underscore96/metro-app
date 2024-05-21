import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Input, NgModule } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  
  
  isMenuOpen: boolean = false;
  private isLoggedInFlag = false;

  
  constructor(private router: Router, private http: HttpClient) { }
  
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  isLoggedIn(): boolean {
    return this.isLoggedInFlag;
  }

  logout(): void {
    this.isLoggedInFlag = false;
    this.router.navigate(['/login']);
    localStorage.removeItem('loggedInUser'); // Remove user data from localStorage
  }

  


}
