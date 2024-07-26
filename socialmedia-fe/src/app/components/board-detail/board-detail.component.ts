import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, RouterLink, RouterOutlet} from '@angular/router';
import {BoardService} from "../../services/board/board.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-board-detail',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './board-detail.component.html',
  styleUrls: ['./board-detail.component.css']
})
export class BoardDetailComponent implements OnInit {
  board: any;

  constructor(
    private route: ActivatedRoute,
    private boardService: BoardService
  ) { }

  ngOnInit(): void {
    const boardId = this.route.snapshot.paramMap.get('boardId');
    this.boardService.getBoard(<string>boardId).subscribe(data => this.board = data);
  }

  deleteBoard(): void {
    this.boardService.deleteBoard(this.board.id).subscribe(() => {
      // Handle successful delete, e.g., navigate back to boards list
    });
  }


}
