import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import { EntityArrayResponseType, AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';
import { AreaContabilEmpresaDeleteDialogComponent } from '../delete/area-contabil-empresa-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-empresa',
  templateUrl: './area-contabil-empresa.component.html',
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
export class AreaContabilEmpresaComponent implements OnInit {
  subscription: Subscription | null = null;
  areaContabilEmpresas?: IAreaContabilEmpresa[];
  isLoading = false;

  sortState = sortStateSignal({});

  public router = inject(Router);
  protected areaContabilEmpresaService = inject(AreaContabilEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IAreaContabilEmpresa): number => this.areaContabilEmpresaService.getAreaContabilEmpresaIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.areaContabilEmpresas || this.areaContabilEmpresas.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(areaContabilEmpresa: IAreaContabilEmpresa): void {
    const modalRef = this.modalService.open(AreaContabilEmpresaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.areaContabilEmpresa = areaContabilEmpresa;
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
    this.areaContabilEmpresas = this.refineData(dataFromBody);
  }

  protected refineData(data: IAreaContabilEmpresa[]): IAreaContabilEmpresa[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IAreaContabilEmpresa[] | null): IAreaContabilEmpresa[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.areaContabilEmpresaService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
