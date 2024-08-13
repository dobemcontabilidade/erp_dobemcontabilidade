import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IParcelamentoImposto } from 'app/entities/parcelamento-imposto/parcelamento-imposto.model';
import { ParcelamentoImpostoService } from 'app/entities/parcelamento-imposto/service/parcelamento-imposto.service';
import { IImpostoAPagarEmpresa } from 'app/entities/imposto-a-pagar-empresa/imposto-a-pagar-empresa.model';
import { ImpostoAPagarEmpresaService } from 'app/entities/imposto-a-pagar-empresa/service/imposto-a-pagar-empresa.service';
import { ImpostoParceladoService } from '../service/imposto-parcelado.service';
import { IImpostoParcelado } from '../imposto-parcelado.model';
import { ImpostoParceladoFormService, ImpostoParceladoFormGroup } from './imposto-parcelado-form.service';

@Component({
  standalone: true,
  selector: 'jhi-imposto-parcelado-update',
  templateUrl: './imposto-parcelado-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ImpostoParceladoUpdateComponent implements OnInit {
  isSaving = false;
  impostoParcelado: IImpostoParcelado | null = null;

  parcelamentoImpostosSharedCollection: IParcelamentoImposto[] = [];
  impostoAPagarEmpresasSharedCollection: IImpostoAPagarEmpresa[] = [];

  protected impostoParceladoService = inject(ImpostoParceladoService);
  protected impostoParceladoFormService = inject(ImpostoParceladoFormService);
  protected parcelamentoImpostoService = inject(ParcelamentoImpostoService);
  protected impostoAPagarEmpresaService = inject(ImpostoAPagarEmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ImpostoParceladoFormGroup = this.impostoParceladoFormService.createImpostoParceladoFormGroup();

  compareParcelamentoImposto = (o1: IParcelamentoImposto | null, o2: IParcelamentoImposto | null): boolean =>
    this.parcelamentoImpostoService.compareParcelamentoImposto(o1, o2);

  compareImpostoAPagarEmpresa = (o1: IImpostoAPagarEmpresa | null, o2: IImpostoAPagarEmpresa | null): boolean =>
    this.impostoAPagarEmpresaService.compareImpostoAPagarEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ impostoParcelado }) => {
      this.impostoParcelado = impostoParcelado;
      if (impostoParcelado) {
        this.updateForm(impostoParcelado);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const impostoParcelado = this.impostoParceladoFormService.getImpostoParcelado(this.editForm);
    if (impostoParcelado.id !== null) {
      this.subscribeToSaveResponse(this.impostoParceladoService.update(impostoParcelado));
    } else {
      this.subscribeToSaveResponse(this.impostoParceladoService.create(impostoParcelado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImpostoParcelado>>): void {
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

  protected updateForm(impostoParcelado: IImpostoParcelado): void {
    this.impostoParcelado = impostoParcelado;
    this.impostoParceladoFormService.resetForm(this.editForm, impostoParcelado);

    this.parcelamentoImpostosSharedCollection =
      this.parcelamentoImpostoService.addParcelamentoImpostoToCollectionIfMissing<IParcelamentoImposto>(
        this.parcelamentoImpostosSharedCollection,
        impostoParcelado.parcelamentoImposto,
      );
    this.impostoAPagarEmpresasSharedCollection =
      this.impostoAPagarEmpresaService.addImpostoAPagarEmpresaToCollectionIfMissing<IImpostoAPagarEmpresa>(
        this.impostoAPagarEmpresasSharedCollection,
        impostoParcelado.impostoAPagarEmpresa,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.parcelamentoImpostoService
      .query()
      .pipe(map((res: HttpResponse<IParcelamentoImposto[]>) => res.body ?? []))
      .pipe(
        map((parcelamentoImpostos: IParcelamentoImposto[]) =>
          this.parcelamentoImpostoService.addParcelamentoImpostoToCollectionIfMissing<IParcelamentoImposto>(
            parcelamentoImpostos,
            this.impostoParcelado?.parcelamentoImposto,
          ),
        ),
      )
      .subscribe((parcelamentoImpostos: IParcelamentoImposto[]) => (this.parcelamentoImpostosSharedCollection = parcelamentoImpostos));

    this.impostoAPagarEmpresaService
      .query()
      .pipe(map((res: HttpResponse<IImpostoAPagarEmpresa[]>) => res.body ?? []))
      .pipe(
        map((impostoAPagarEmpresas: IImpostoAPagarEmpresa[]) =>
          this.impostoAPagarEmpresaService.addImpostoAPagarEmpresaToCollectionIfMissing<IImpostoAPagarEmpresa>(
            impostoAPagarEmpresas,
            this.impostoParcelado?.impostoAPagarEmpresa,
          ),
        ),
      )
      .subscribe((impostoAPagarEmpresas: IImpostoAPagarEmpresa[]) => (this.impostoAPagarEmpresasSharedCollection = impostoAPagarEmpresas));
  }
}
