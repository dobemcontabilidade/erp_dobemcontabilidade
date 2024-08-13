import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { EntityArrayResponseType, EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';
import { EtapaFluxoExecucaoDeleteDialogComponent } from '../delete/etapa-fluxo-execucao-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-etapa-fluxo-execucao',
  templateUrl: './etapa-fluxo-execucao.component.html',
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
export class EtapaFluxoExecucaoComponent implements OnInit {
  subscription: Subscription | null = null;
  etapaFluxoExecucaos?: IEtapaFluxoExecucao[];
  isLoading = false;

  sortState = sortStateSignal({});

  public router = inject(Router);
  protected etapaFluxoExecucaoService = inject(EtapaFluxoExecucaoService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IEtapaFluxoExecucao): number => this.etapaFluxoExecucaoService.getEtapaFluxoExecucaoIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.etapaFluxoExecucaos || this.etapaFluxoExecucaos.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(etapaFluxoExecucao: IEtapaFluxoExecucao): void {
    const modalRef = this.modalService.open(EtapaFluxoExecucaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etapaFluxoExecucao = etapaFluxoExecucao;
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
    this.etapaFluxoExecucaos = this.refineData(dataFromBody);
  }

  protected refineData(data: IEtapaFluxoExecucao[]): IEtapaFluxoExecucao[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IEtapaFluxoExecucao[] | null): IEtapaFluxoExecucao[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.etapaFluxoExecucaoService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
