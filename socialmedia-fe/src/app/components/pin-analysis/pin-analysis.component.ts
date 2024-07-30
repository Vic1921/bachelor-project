import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PinService } from '../../services/pin/pin.service';
import { PinDTO } from '../../models/pin-dto';
import {PieChartComponent} from "../pie-chart/pie-chart.component";
import {CategoryTableComponent} from "../category-table/category-table.component";

@Component({
  selector: 'app-pin-analysis',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, PieChartComponent, CategoryTableComponent],
  templateUrl: './pin-analysis.component.html',
  styleUrls: ['./pin-analysis.component.css']
})
export class PinAnalysisComponent implements OnInit {
  pin: PinDTO | undefined;
  pinId: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private pinService: PinService
  ) { }

  ngOnInit(): void {
    this.pinId = this.route.snapshot.paramMap.get('pinId');
    this.pinService.getPin(<string>this.pinId).subscribe(data => this.pin = data);
  }
}
