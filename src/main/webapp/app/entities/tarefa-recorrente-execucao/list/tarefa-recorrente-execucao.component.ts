import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';
import { EntityArrayResponseType, TarefaRecorrenteExecucaoService } from '../service/tarefa-recorrente-execucao.service';
import { TarefaRecorrenteExecucaoDeleteDialogComponent } from '../delete/tarefa-recorrente-execucao-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-tarefa-recorrente-execucao',
  templateUrl: './tarefa-recorrente-execucao.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
  ],
})
export class TarefaRecorrenteExecucaoComponent implements OnInit {
  subscription: Subscription | null = null;
  tarefaRecorrenteExecucaos?: ITarefaRecorrenteExecucao[];
  isLoading = false;

  sortState = sortStateSignal({});

  public router = inject(Router);
  protected tarefaRecorrenteExecucaoService = inject(TarefaRecorrenteExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: ITarefaRecorrenteExecucao): number =>
    this.tarefaRecorrenteExecucaoService.getTarefaRecorrenteExecucaoIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.tarefaRecorrenteExecucaos || this.tarefaRecorrenteExecucaos.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao): void {
    const modalRef = this.modalService.open(TarefaRecorrenteExecucaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.tarefaRecorrenteExecucaos = this.refineData(dataFromBody);
  }

  protected refineData(data: ITarefaRecorrenteExecucao[]): ITarefaRecorrenteExecucao[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ITarefaRecorrenteExecucao[] | null): ITarefaRecorrenteExecucao[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.tarefaRecorrenteExecucaoService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(sortState: SortState): void {
    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
