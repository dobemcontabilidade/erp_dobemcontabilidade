import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioService } from '../service/agente-integracao-estagio.service';
import { AgenteIntegracaoEstagioFormService, AgenteIntegracaoEstagioFormGroup } from './agente-integracao-estagio-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agente-integracao-estagio-update',
  templateUrl: './agente-integracao-estagio-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgenteIntegracaoEstagioUpdateComponent implements OnInit {
  isSaving = false;
  agenteIntegracaoEstagio: IAgenteIntegracaoEstagio | null = null;

  cidadesSharedCollection: ICidade[] = [];

  protected agenteIntegracaoEstagioService = inject(AgenteIntegracaoEstagioService);
  protected agenteIntegracaoEstagioFormService = inject(AgenteIntegracaoEstagioFormService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgenteIntegracaoEstagioFormGroup = this.agenteIntegracaoEstagioFormService.createAgenteIntegracaoEstagioFormGroup();

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agenteIntegracaoEstagio }) => {
      this.agenteIntegracaoEstagio = agenteIntegracaoEstagio;
      if (agenteIntegracaoEstagio) {
        this.updateForm(agenteIntegracaoEstagio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agenteIntegracaoEstagio = this.agenteIntegracaoEstagioFormService.getAgenteIntegracaoEstagio(this.editForm);
    if (agenteIntegracaoEstagio.id !== null) {
      this.subscribeToSaveResponse(this.agenteIntegracaoEstagioService.update(agenteIntegracaoEstagio));
    } else {
      this.subscribeToSaveResponse(this.agenteIntegracaoEstagioService.create(agenteIntegracaoEstagio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgenteIntegracaoEstagio>>): void {
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

  protected updateForm(agenteIntegracaoEstagio: IAgenteIntegracaoEstagio): void {
    this.agenteIntegracaoEstagio = agenteIntegracaoEstagio;
    this.agenteIntegracaoEstagioFormService.resetForm(this.editForm, agenteIntegracaoEstagio);

    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      agenteIntegracaoEstagio.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(
        map((cidades: ICidade[]) =>
          this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.agenteIntegracaoEstagio?.cidade),
        ),
      )
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
