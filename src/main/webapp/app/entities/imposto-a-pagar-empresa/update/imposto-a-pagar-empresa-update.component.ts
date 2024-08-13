import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IImpostoEmpresa } from 'app/entities/imposto-empresa/imposto-empresa.model';
import { ImpostoEmpresaService } from 'app/entities/imposto-empresa/service/imposto-empresa.service';
import { SituacaoPagamentoImpostoEnum } from 'app/entities/enumerations/situacao-pagamento-imposto-enum.model';
import { ImpostoAPagarEmpresaService } from '../service/imposto-a-pagar-empresa.service';
import { IImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';
import { ImpostoAPagarEmpresaFormService, ImpostoAPagarEmpresaFormGroup } from './imposto-a-pagar-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-imposto-a-pagar-empresa-update',
  templateUrl: './imposto-a-pagar-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ImpostoAPagarEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  impostoAPagarEmpresa: IImpostoAPagarEmpresa | null = null;
  situacaoPagamentoImpostoEnumValues = Object.keys(SituacaoPagamentoImpostoEnum);

  impostoEmpresasSharedCollection: IImpostoEmpresa[] = [];

  protected impostoAPagarEmpresaService = inject(ImpostoAPagarEmpresaService);
  protected impostoAPagarEmpresaFormService = inject(ImpostoAPagarEmpresaFormService);
  protected impostoEmpresaService = inject(ImpostoEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ImpostoAPagarEmpresaFormGroup = this.impostoAPagarEmpresaFormService.createImpostoAPagarEmpresaFormGroup();

  compareImpostoEmpresa = (o1: IImpostoEmpresa | null, o2: IImpostoEmpresa | null): boolean =>
    this.impostoEmpresaService.compareImpostoEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ impostoAPagarEmpresa }) => {
      this.impostoAPagarEmpresa = impostoAPagarEmpresa;
      if (impostoAPagarEmpresa) {
        this.updateForm(impostoAPagarEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const impostoAPagarEmpresa = this.impostoAPagarEmpresaFormService.getImpostoAPagarEmpresa(this.editForm);
    if (impostoAPagarEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.impostoAPagarEmpresaService.update(impostoAPagarEmpresa));
    } else {
      this.subscribeToSaveResponse(this.impostoAPagarEmpresaService.create(impostoAPagarEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImpostoAPagarEmpresa>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(impostoAPagarEmpresa: IImpostoAPagarEmpresa): void {
    this.impostoAPagarEmpresa = impostoAPagarEmpresa;
    this.impostoAPagarEmpresaFormService.resetForm(this.editForm, impostoAPagarEmpresa);

    this.impostoEmpresasSharedCollection = this.impostoEmpresaService.addImpostoEmpresaToCollectionIfMissing<IImpostoEmpresa>(
      this.impostoEmpresasSharedCollection,
      impostoAPagarEmpresa.imposto,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.impostoEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IImpostoEmpresa[]>) => res.body ?? []))
      .pipe(
        map((impostoEmpresas: IImpostoEmpresa[]) =>
          this.impostoEmpresaService.addImpostoEmpresaToCollectionIfMissing<IImpostoEmpresa>(
            impostoEmpresas,
            this.impostoAPagarEmpresa?.imposto,
          ),
        ),
      )
      .subscribe((impostoEmpresas: IImpostoEmpresa[]) => (this.impostoEmpresasSharedCollection = impostoEmpresas));
  }
}
