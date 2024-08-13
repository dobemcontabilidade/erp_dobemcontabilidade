import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import { EntityArrayResponseType, DependentesFuncionarioService } from '../service/dependentes-funcionario.service';
import { DependentesFuncionarioDeleteDialogComponent } from '../delete/dependentes-funcionario-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-dependentes-funcionario',
  templateUrl: './dependentes-funcionario.component.html',
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
export class DependentesFuncionarioComponent implements OnInit {
  subscription: Subscription | null = null;
  dependentesFuncionarios?: IDependentesFuncionario[];
  isLoading = false;

  sortState = sortStateSignal({});

  public router = inject(Router);
  protected dependentesFuncionarioService = inject(DependentesFuncionarioService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IDependentesFuncionario): number =>
    this.dependentesFuncionarioService.getDependentesFuncionarioIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.dependentesFuncionarios || this.dependentesFuncionarios.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(dependentesFuncionario: IDependentesFuncionario): void {
    const modalRef = this.modalService.open(DependentesFuncionarioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dependentesFuncionario = dependentesFuncionario;
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
    this.dependentesFuncionarios = this.refineData(dataFromBody);
  }

  protected refineData(data: IDependentesFuncionario[]): IDependentesFuncionario[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IDependentesFuncionario[] | null): IDependentesFuncionario[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      eagerload: true,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.dependentesFuncionarioService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
