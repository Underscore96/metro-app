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
export class AdministComponent implements OnInit{

  

    // nomeLineaVerde: string = 'verde';
    // nomeLineaBlu: string = 'blu';
    // metroDataVerde: any[] = [];
    // metroDataBlu: any[] = [];
    metroLines: any[] = [];
    nomeLinea: string = 'verde';
    metroData: any[] = [];
  

    constructor(private http: HttpClient) {}
   


    ngOnInit():void{
        // this.getMetroData(this.nomeLineaVerde, 'metroDataVerde');
        // this.getMetroData(this.nomeLineaBlu, 'metroDataBlu');
        this.getMetroData()
        }
    
    
        // getMetroData(linea: string, dataVariable: 'metroDataVerde' | 'metroDataBlu') {
        //     const url = `http://localhost:8080/Metro/rest/linea/leggilinea?nomeLinea=${linea}`;
        //     this.http.get<any[]>(url).subscribe(data => {
        //         this[dataVariable] = data;
        //     });
        // }

          
        getMetroData() {
            const url = `http://localhost:8080/Metro/rest/fermata/tutte`;
            this.http.get<any[]>(url).subscribe(data => {
              this.metroData = data;
            });
          }

          deleteStop(fermata: any) {
            const stopId = fermata.numFermata;
            const url = `http://localhost:8080/Metro/rest/fermata/cancella?numFermata=${stopId}`; 
            
            this.http.delete(url).subscribe(
              () => {
                console.log(`Stop ${stopId} deleted successfully.`);
                
    
                this.metroData = this.metroData.filter(stop => stop.numFermata !== stopId);
              },
              (error) => {
                console.error('Error deleting stop:', error);
              }
            );
          }
          
          


        // deleteStop(fermata: any, dataVariable: 'metroDataVerde' | 'metroDataBlu') {
        //     const stopId = fermata.numFermata;
        //     const url = `http://localhost:8080/Metro/rest/fermata/cancella?numFermata=${stopId}`;
            
        //     this.http.delete(url).subscribe(
        //         () => {
        //             console.log(`Stop ${stopId} deleted successfully.`);
        //             this[dataVariable] = this[dataVariable].filter(stop => stop.numFermata !== stopId);
        //         },
        //         (error) => {
        //             console.error('Error deleting stop:', error);
        //         }
        //     );
        // }
        
        // renameStop(fermata: any, dataVariable: 'metroDataVerde' | 'metroDataBlu') {
        //     const stopId = fermata.numFermata;
        //     const newName = prompt('Enter the new name for the stop:', fermata.nome);
            
        //     if (newName !== null) {
        //         const url = `http://localhost:8080/Metro/rest/fermata/rinomina?numFermata=${stopId}&nomeFermata=${newName}`;
                
        //         this.http.put(url, null).subscribe(
        //             () => {
        //                 console.log(`Stop ${stopId} renamed to ${newName} successfully.`);
        //                 fermata.nome = newName;
        //             },
        //             (error) => {
        //                 console.error('Error renaming stop:', error);
        //             }
        //         );
        //     }
        // }
          
        
        renameStop(fermata: any) {
            const stopId = fermata.numFermata;
            const newName = prompt('Metti il nuovo nome:', fermata.nome);
          
            if (newName !== null) {
              const url = `http://localhost:8080/Metro/rest/fermata/rinomina?numFermata=${stopId}&nomeFermata=${newName}`;
          
              this.http.put(url, null).subscribe(
                () => {
                  console.log(`Stop ${stopId} renamed to ${newName} successfully.`);
                  fermata.nome = newName;
                },
                (error) => {
                  console.error('Error renaming stop:', error);
                }
              );
            }
          }
 
        }