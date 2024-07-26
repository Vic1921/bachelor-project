import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import {RouterLink } from "@angular/router";
import { BoardService } from "../../services/board/board.service";
import {CommonModule} from "@angular/common";
import { BoardDto } from "../../models/board-dto";

@Component({
  selector: 'app-boards',
  standalone: true,
  imports: [ RouterLink, CommonModule],
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.css']
})
export class BoardsComponent implements OnInit {
  boards$: Observable<BoardDto[]> = of([]);

  constructor(private boardService: BoardService) { }

  ngOnInit(): void {
    this.boards$ = this.boardService.getBoards();
  }

}
