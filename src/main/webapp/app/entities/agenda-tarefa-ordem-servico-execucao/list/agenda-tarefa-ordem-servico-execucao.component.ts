import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';
import { EntityArrayResponseType, AgendaTarefaOrdemServicoExecucaoService } from '../service/agenda-tarefa-ordem-servico-execucao.service';
import { AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent } from '../delete/agenda-tarefa-ordem-servico-execucao-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-agenda-tarefa-ordem-servico-execucao',
  templateUrl: './agenda-tarefa-ordem-servico-execucao.component.html',
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
export class AgendaTarefaOrdemServicoExecucaoComponent implements OnInit {
  subscription: Subscription | null = null;
  agendaTarefaOrdemServicoExecucaos?: IAgendaTarefaOrdemServicoExecucao[];
  isLoading = false;

  sortState = sortStateSignal({});

  public router = inject(Router);
  protected agendaTarefaOrdemServicoExecucaoService = inject(AgendaTarefaOrdemServicoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IAgendaTarefaOrdemServicoExecucao): number =>
    this.agendaTarefaOrdemServicoExecucaoService.getAgendaTarefaOrdemServicoExecucaoIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.agendaTarefaOrdemServicoExecucaos || this.agendaTarefaOrdemServicoExecucaos.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao): void {
    const modalRef = this.modalService.open(AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucao;
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
    this.agendaTarefaOrdemServicoExecucaos = this.refineData(dataFromBody);
  }

  protected refineData(data: IAgendaTarefaOrdemServicoExecucao[]): IAgendaTarefaOrdemServicoExecucao[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IAgendaTarefaOrdemServicoExecucao[] | null): IAgendaTarefaOrdemServicoExecucao[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.agendaTarefaOrdemServicoExecucaoService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
