import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';
import {BoardService} from "../../services/board/board.service";
import {CommonModule} from "@angular/common";
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-board-detail',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, CardModule, ButtonModule],
  templateUrl: './board-detail.component.html',
  styleUrls: ['./board-detail.component.css']
})
export class BoardDetailComponent implements OnInit {
  board: any;

  constructor(
    private route: ActivatedRoute,
    private boardService: BoardService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const boardId = this.route.snapshot.paramMap.get('boardId');
    this.boardService.getBoard(<string>boardId).subscribe(data => this.board = data);
  }

  deleteBoard(): void {
    this.boardService.deleteBoard(this.board.id).subscribe(() => {
      this.router.navigate(['/boards']);
    });
  }

}
