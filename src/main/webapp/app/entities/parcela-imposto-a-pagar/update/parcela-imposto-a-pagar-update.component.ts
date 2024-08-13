import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IParcelamentoImposto } from 'app/entities/parcelamento-imposto/parcelamento-imposto.model';
import { ParcelamentoImpostoService } from 'app/entities/parcelamento-imposto/service/parcelamento-imposto.service';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { ParcelaImpostoAPagarService } from '../service/parcela-imposto-a-pagar.service';
import { IParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';
import { ParcelaImpostoAPagarFormService, ParcelaImpostoAPagarFormGroup } from './parcela-imposto-a-pagar-form.service';

@Component({
  standalone: true,
  selector: 'jhi-parcela-imposto-a-pagar-update',
  templateUrl: './parcela-imposto-a-pagar-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ParcelaImpostoAPagarUpdateComponent implements OnInit {
  isSaving = false;
  parcelaImpostoAPagar: IParcelaImpostoAPagar | null = null;
  mesCompetenciaEnumValues = Object.keys(MesCompetenciaEnum);

  parcelamentoImpostosSharedCollection: IParcelamentoImposto[] = [];

  protected parcelaImpostoAPagarService = inject(ParcelaImpostoAPagarService);
  protected parcelaImpostoAPagarFormService = inject(ParcelaImpostoAPagarFormService);
  protected parcelamentoImpostoService = inject(ParcelamentoImpostoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ParcelaImpostoAPagarFormGroup = this.parcelaImpostoAPagarFormService.createParcelaImpostoAPagarFormGroup();

  compareParcelamentoImposto = (o1: IParcelamentoImposto | null, o2: IParcelamentoImposto | null): boolean =>
    this.parcelamentoImpostoService.compareParcelamentoImposto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parcelaImpostoAPagar }) => {
      this.parcelaImpostoAPagar = parcelaImpostoAPagar;
      if (parcelaImpostoAPagar) {
        this.updateForm(parcelaImpostoAPagar);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parcelaImpostoAPagar = this.parcelaImpostoAPagarFormService.getParcelaImpostoAPagar(this.editForm);
    if (parcelaImpostoAPagar.id !== null) {
      this.subscribeToSaveResponse(this.parcelaImpostoAPagarService.update(parcelaImpostoAPagar));
    } else {
      this.subscribeToSaveResponse(this.parcelaImpostoAPagarService.create(parcelaImpostoAPagar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParcelaImpostoAPagar>>): void {
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

  protected updateForm(parcelaImpostoAPagar: IParcelaImpostoAPagar): void {
    this.parcelaImpostoAPagar = parcelaImpostoAPagar;
    this.parcelaImpostoAPagarFormService.resetForm(this.editForm, parcelaImpostoAPagar);

    this.parcelamentoImpostosSharedCollection =
      this.parcelamentoImpostoService.addParcelamentoImpostoToCollectionIfMissing<IParcelamentoImposto>(
        this.parcelamentoImpostosSharedCollection,
        parcelaImpostoAPagar.parcelamentoImposto,
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
            this.parcelaImpostoAPagar?.parcelamentoImposto,
          ),
        ),
      )
      .subscribe((parcelamentoImpostos: IParcelamentoImposto[]) => (this.parcelamentoImpostosSharedCollection = parcelamentoImpostos));
  }
}
