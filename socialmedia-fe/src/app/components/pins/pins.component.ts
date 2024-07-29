import {Component, OnInit} from '@angular/core';
import { RouterLink } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { PinService } from "../../services/pin/pin.service";
import {Observable, of} from "rxjs";
import {PinDTO} from "../../models/pin-dto";

@Component({
  selector: 'app-pins',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './pins.component.html',
  styleUrl: './pins.component.css'
})
export class PinsComponent implements OnInit {
  pins$: Observable<PinDTO[]> = of([]);

  constructor(private pinService: PinService) { }

  ngOnInit(): void {
    this.pins$ = this.pinService.getPins();
  }


}
