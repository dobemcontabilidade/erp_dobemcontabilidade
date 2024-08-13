import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { SituacaoSolicitacaoParcelamentoEnum } from 'app/entities/enumerations/situacao-solicitacao-parcelamento-enum.model';
import { ParcelamentoImpostoService } from '../service/parcelamento-imposto.service';
import { IParcelamentoImposto } from '../parcelamento-imposto.model';
import { ParcelamentoImpostoFormService, ParcelamentoImpostoFormGroup } from './parcelamento-imposto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-parcelamento-imposto-update',
  templateUrl: './parcelamento-imposto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ParcelamentoImpostoUpdateComponent implements OnInit {
  isSaving = false;
  parcelamentoImposto: IParcelamentoImposto | null = null;
  situacaoSolicitacaoParcelamentoEnumValues = Object.keys(SituacaoSolicitacaoParcelamentoEnum);

  impostosSharedCollection: IImposto[] = [];
  empresasSharedCollection: IEmpresa[] = [];

  protected parcelamentoImpostoService = inject(ParcelamentoImpostoService);
  protected parcelamentoImpostoFormService = inject(ParcelamentoImpostoFormService);
  protected impostoService = inject(ImpostoService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ParcelamentoImpostoFormGroup = this.parcelamentoImpostoFormService.createParcelamentoImpostoFormGroup();

  compareImposto = (o1: IImposto | null, o2: IImposto | null): boolean => this.impostoService.compareImposto(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parcelamentoImposto }) => {
      this.parcelamentoImposto = parcelamentoImposto;
      if (parcelamentoImposto) {
        this.updateForm(parcelamentoImposto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parcelamentoImposto = this.parcelamentoImpostoFormService.getParcelamentoImposto(this.editForm);
    if (parcelamentoImposto.id !== null) {
      this.subscribeToSaveResponse(this.parcelamentoImpostoService.update(parcelamentoImposto));
    } else {
      this.subscribeToSaveResponse(this.parcelamentoImpostoService.create(parcelamentoImposto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParcelamentoImposto>>): void {
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

  protected updateForm(parcelamentoImposto: IParcelamentoImposto): void {
    this.parcelamentoImposto = parcelamentoImposto;
    this.parcelamentoImpostoFormService.resetForm(this.editForm, parcelamentoImposto);

    this.impostosSharedCollection = this.impostoService.addImpostoToCollectionIfMissing<IImposto>(
      this.impostosSharedCollection,
      parcelamentoImposto.imposto,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      parcelamentoImposto.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.impostoService
      .query()
      .pipe(map((res: HttpResponse<IImposto[]>) => res.body ?? []))
      .pipe(
        map((impostos: IImposto[]) =>
          this.impostoService.addImpostoToCollectionIfMissing<IImposto>(impostos, this.parcelamentoImposto?.imposto),
        ),
      )
      .subscribe((impostos: IImposto[]) => (this.impostosSharedCollection = impostos));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.parcelamentoImposto?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
