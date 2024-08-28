import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { AdicionalEnquadramentoService } from '../service/adicional-enquadramento.service';
import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';
import { AdicionalEnquadramentoFormService, AdicionalEnquadramentoFormGroup } from './adicional-enquadramento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-adicional-enquadramento-update',
  templateUrl: './adicional-enquadramento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AdicionalEnquadramentoUpdateComponent implements OnInit {
  isSaving = false;
  adicionalEnquadramento: IAdicionalEnquadramento | null = null;

  enquadramentosSharedCollection: IEnquadramento[] = [];
  planoAssinaturaContabilsSharedCollection: IPlanoAssinaturaContabil[] = [];

  protected adicionalEnquadramentoService = inject(AdicionalEnquadramentoService);
  protected adicionalEnquadramentoFormService = inject(AdicionalEnquadramentoFormService);
  protected enquadramentoService = inject(EnquadramentoService);
  protected planoAssinaturaContabilService = inject(PlanoAssinaturaContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AdicionalEnquadramentoFormGroup = this.adicionalEnquadramentoFormService.createAdicionalEnquadramentoFormGroup();

  compareEnquadramento = (o1: IEnquadramento | null, o2: IEnquadramento | null): boolean =>
    this.enquadramentoService.compareEnquadramento(o1, o2);

  comparePlanoAssinaturaContabil = (o1: IPlanoAssinaturaContabil | null, o2: IPlanoAssinaturaContabil | null): boolean =>
    this.planoAssinaturaContabilService.comparePlanoAssinaturaContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adicionalEnquadramento }) => {
      this.adicionalEnquadramento = adicionalEnquadramento;
      if (adicionalEnquadramento) {
        this.updateForm(adicionalEnquadramento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adicionalEnquadramento = this.adicionalEnquadramentoFormService.getAdicionalEnquadramento(this.editForm);
    if (adicionalEnquadramento.id !== null) {
      this.subscribeToSaveResponse(this.adicionalEnquadramentoService.update(adicionalEnquadramento));
    } else {
      this.subscribeToSaveResponse(this.adicionalEnquadramentoService.create(adicionalEnquadramento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdicionalEnquadramento>>): void {
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

  protected updateForm(adicionalEnquadramento: IAdicionalEnquadramento): void {
    this.adicionalEnquadramento = adicionalEnquadramento;
    this.adicionalEnquadramentoFormService.resetForm(this.editForm, adicionalEnquadramento);

    this.enquadramentosSharedCollection = this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
      this.enquadramentosSharedCollection,
      adicionalEnquadramento.enquadramento,
    );
    this.planoAssinaturaContabilsSharedCollection =
      this.planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing<IPlanoAssinaturaContabil>(
        this.planoAssinaturaContabilsSharedCollection,
        adicionalEnquadramento.planoAssinaturaContabil,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.enquadramentoService
      .query()
      .pipe(map((res: HttpResponse<IEnquadramento[]>) => res.body ?? []))
      .pipe(
        map((enquadramentos: IEnquadramento[]) =>
          this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
            enquadramentos,
            this.adicionalEnquadramento?.enquadramento,
          ),
        ),
      )
      .subscribe((enquadramentos: IEnquadramento[]) => (this.enquadramentosSharedCollection = enquadramentos));

    this.planoAssinaturaContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoAssinaturaContabil[]>) => res.body ?? []))
      .pipe(
        map((planoAssinaturaContabils: IPlanoAssinaturaContabil[]) =>
          this.planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing<IPlanoAssinaturaContabil>(
            planoAssinaturaContabils,
            this.adicionalEnquadramento?.planoAssinaturaContabil,
          ),
        ),
      )
      .subscribe(
        (planoAssinaturaContabils: IPlanoAssinaturaContabil[]) =>
          (this.planoAssinaturaContabilsSharedCollection = planoAssinaturaContabils),
      );
  }
}
