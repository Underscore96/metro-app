import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoggedInFlag = false;

  constructor(private router: Router, private http: HttpClient) { }

  isLoggedIn(): boolean {
    return this.isLoggedInFlag;
  }


  login(username: string, password: string): void {
    const url = `http://localhost:8080/Metro/rest/login?nomeUtente=${username}&password=${password}`;
    this.http.get<any[]>(url).subscribe(
      data => {
        if (data && data.length > 0) {
          this.isLoggedInFlag = true; 
          this.router.navigate(['/admin']);
        } else {
          console.log('Invalid username or password. Access denied.');
        }
      },
      error => {
        console.error('Error during login:', error);
      }
    );
  }

  logout(): void {

    this.isLoggedInFlag = false;
  }
 


}
