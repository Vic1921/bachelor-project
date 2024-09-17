import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { RouterLink } from '@angular/router';
import { BoardService } from '../../services/board/board.service';
import { CommonModule } from '@angular/common';
import { BoardDto } from '../../models/board-dto';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';


@Component({
  selector: 'app-boards',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, CardModule, ButtonModule, InputTextModule,],
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.css']
})
export class BoardsComponent implements OnInit {
  boards$: Observable<BoardDto[]> = of([]);
  newBoardName: string = '';
  newBoardDescription: string = '';

  constructor(private boardService: BoardService) { }

  ngOnInit(): void {
    this.boards$ = this.boardService.getBoards();
  }

  createBoard(): void {
    const newBoard = { name: this.newBoardName, description: this.newBoardDescription };
    this.boardService.createBoard(newBoard).subscribe(() => {
      this.boards$ = this.boardService.getBoards(); // Refresh the list after creation
    });
  }

  deleteBoard(boardId: string): void {
    this.boardService.deleteBoard(boardId).subscribe(() => {
      this.boards$ = this.boardService.getBoards(); // Refresh the list after deletion
    });
  }
}
