import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';
import { EntityArrayResponseType, GrupoAcessoUsuarioContadorService } from '../service/grupo-acesso-usuario-contador.service';
import { GrupoAcessoUsuarioContadorDeleteDialogComponent } from '../delete/grupo-acesso-usuario-contador-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-grupo-acesso-usuario-contador',
  templateUrl: './grupo-acesso-usuario-contador.component.html',
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
export class GrupoAcessoUsuarioContadorComponent implements OnInit {
  subscription: Subscription | null = null;
  grupoAcessoUsuarioContadors?: IGrupoAcessoUsuarioContador[];
  isLoading = false;

  sortState = sortStateSignal({});

  public router = inject(Router);
  protected grupoAcessoUsuarioContadorService = inject(GrupoAcessoUsuarioContadorService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IGrupoAcessoUsuarioContador): number =>
    this.grupoAcessoUsuarioContadorService.getGrupoAcessoUsuarioContadorIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.grupoAcessoUsuarioContadors || this.grupoAcessoUsuarioContadors.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador): void {
    const modalRef = this.modalService.open(GrupoAcessoUsuarioContadorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.grupoAcessoUsuarioContador = grupoAcessoUsuarioContador;
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
    this.grupoAcessoUsuarioContadors = this.refineData(dataFromBody);
  }

  protected refineData(data: IGrupoAcessoUsuarioContador[]): IGrupoAcessoUsuarioContador[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IGrupoAcessoUsuarioContador[] | null): IGrupoAcessoUsuarioContador[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.grupoAcessoUsuarioContadorService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
