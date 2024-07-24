import { Component, OnInit } from '@angular/core';
import { PinService } from '../../services/pin.service';

@Component({
  selector: 'app-pin-list',
  templateUrl: './pin-list.component.html',
  styleUrls: ['./pin-list.component.css']
})
export class PinListComponent implements OnInit {
  pins: any[] = [];

  constructor(private pinService: PinService) { }

  ngOnInit(): void {
    this.pinService.getPins().subscribe(
      data => this.pins = data,
      error => console.error('Error fetching pins:', error)
    );
  }
}
