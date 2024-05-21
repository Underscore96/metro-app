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
      zoom: 13
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);

    const markers = [
      L.marker([44.4072060, 8.9347032], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),
      L.marker([44.4072798, 8.9485139], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),
      L.marker([44.4043523, 8.9316873], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),
      L.marker([44.4130819, 8.9266780], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),
      L.marker([44.4163288, 8.9190287], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),
      L.marker([ 44.4139695, 8.9122189], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),
      L.marker([ 44.4280070, 8.8957011], {
        icon: L.divIcon({
          className: 'custom-icon',
          html: '<div style="background-color: red; width: 16px; height: 16px; border-radius: 50%;"></div>',
          iconSize: [16, 16],
          iconAnchor: [8, 8]
        })
      }),

    ];
    
    
    
    markers.forEach(marker => marker.addTo(this.map));
    
   
    const markerCoordinates = markers.map(marker => marker.getLatLng());
    
    const polyline = L.polyline(markerCoordinates, { color: 'blue' }).addTo(this.map);

}

  
  constructor() { }

  ngAfterViewInit(): void {
    this.initMap();
  }
}





