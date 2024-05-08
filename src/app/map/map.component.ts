import { AfterViewInit, Component, OnInit } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import L, * as Leaflet from 'leaflet';

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [ LeafletModule],
  templateUrl: './map.component.html',
  styleUrl: './map.component.scss'
})
export class MapComponent implements AfterViewInit{
  map: any;

  private initMap(): void {
    this.map = L.map('map', {
      center: [44.4056, 8.9463], 
      zoom: 14
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);
  

    const brignoleMarker = L.marker([44.4072798, 8.9485139], {
      icon: L.divIcon({
        className: 'custom-icon',
        html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
        iconSize: [16, 16],
        iconAnchor: [8, 8]
      })
    }).addTo(this.map);
  }

  
  constructor() { }

  ngAfterViewInit(): void {
    this.initMap();
  }
}






