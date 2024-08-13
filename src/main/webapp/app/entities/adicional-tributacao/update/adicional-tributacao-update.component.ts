import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { AdicionalTributacaoService } from '../service/adicional-tributacao.service';
import { IAdicionalTributacao } from '../adicional-tributacao.model';
import { AdicionalTributacaoFormService, AdicionalTributacaoFormGroup } from './adicional-tributacao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-adicional-tributacao-update',
  templateUrl: './adicional-tributacao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AdicionalTributacaoUpdateComponent implements OnInit {
  isSaving = false;
  adicionalTributacao: IAdicionalTributacao | null = null;

  tributacaosSharedCollection: ITributacao[] = [];
  planoContabilsSharedCollection: IPlanoContabil[] = [];

  protected adicionalTributacaoService = inject(AdicionalTributacaoService);
  protected adicionalTributacaoFormService = inject(AdicionalTributacaoFormService);
  protected tributacaoService = inject(TributacaoService);
  protected planoContabilService = inject(PlanoContabilService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AdicionalTributacaoFormGroup = this.adicionalTributacaoFormService.createAdicionalTributacaoFormGroup();

  compareTributacao = (o1: ITributacao | null, o2: ITributacao | null): boolean => this.tributacaoService.compareTributacao(o1, o2);

  comparePlanoContabil = (o1: IPlanoContabil | null, o2: IPlanoContabil | null): boolean =>
    this.planoContabilService.comparePlanoContabil(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adicionalTributacao }) => {
      this.adicionalTributacao = adicionalTributacao;
      if (adicionalTributacao) {
        this.updateForm(adicionalTributacao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adicionalTributacao = this.adicionalTributacaoFormService.getAdicionalTributacao(this.editForm);
    if (adicionalTributacao.id !== null) {
      this.subscribeToSaveResponse(this.adicionalTributacaoService.update(adicionalTributacao));
    } else {
      this.subscribeToSaveResponse(this.adicionalTributacaoService.create(adicionalTributacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdicionalTributacao>>): void {
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

  protected updateForm(adicionalTributacao: IAdicionalTributacao): void {
    this.adicionalTributacao = adicionalTributacao;
    this.adicionalTributacaoFormService.resetForm(this.editForm, adicionalTributacao);

    this.tributacaosSharedCollection = this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(
      this.tributacaosSharedCollection,
      adicionalTributacao.tributacao,
    );
    this.planoContabilsSharedCollection = this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
      this.planoContabilsSharedCollection,
      adicionalTributacao.planoContabil,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tributacaoService
      .query()
      .pipe(map((res: HttpResponse<ITributacao[]>) => res.body ?? []))
      .pipe(
        map((tributacaos: ITributacao[]) =>
          this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(tributacaos, this.adicionalTributacao?.tributacao),
        ),
      )
      .subscribe((tributacaos: ITributacao[]) => (this.tributacaosSharedCollection = tributacaos));

    this.planoContabilService
      .query()
      .pipe(map((res: HttpResponse<IPlanoContabil[]>) => res.body ?? []))
      .pipe(
        map((planoContabils: IPlanoContabil[]) =>
          this.planoContabilService.addPlanoContabilToCollectionIfMissing<IPlanoContabil>(
            planoContabils,
            this.adicionalTributacao?.planoContabil,
          ),
        ),
      )
      .subscribe((planoContabils: IPlanoContabil[]) => (this.planoContabilsSharedCollection = planoContabils));
  }
}
