import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';
import { InstituicaoEnsinoFormService, InstituicaoEnsinoFormGroup } from './instituicao-ensino-form.service';

@Component({
  standalone: true,
  selector: 'jhi-instituicao-ensino-update',
  templateUrl: './instituicao-ensino-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InstituicaoEnsinoUpdateComponent implements OnInit {
  isSaving = false;
  instituicaoEnsino: IInstituicaoEnsino | null = null;

  cidadesSharedCollection: ICidade[] = [];

  protected instituicaoEnsinoService = inject(InstituicaoEnsinoService);
  protected instituicaoEnsinoFormService = inject(InstituicaoEnsinoFormService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: InstituicaoEnsinoFormGroup = this.instituicaoEnsinoFormService.createInstituicaoEnsinoFormGroup();

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instituicaoEnsino }) => {
      this.instituicaoEnsino = instituicaoEnsino;
      if (instituicaoEnsino) {
        this.updateForm(instituicaoEnsino);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instituicaoEnsino = this.instituicaoEnsinoFormService.getInstituicaoEnsino(this.editForm);
    if (instituicaoEnsino.id !== null) {
      this.subscribeToSaveResponse(this.instituicaoEnsinoService.update(instituicaoEnsino));
    } else {
      this.subscribeToSaveResponse(this.instituicaoEnsinoService.create(instituicaoEnsino));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstituicaoEnsino>>): void {
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

  protected updateForm(instituicaoEnsino: IInstituicaoEnsino): void {
    this.instituicaoEnsino = instituicaoEnsino;
    this.instituicaoEnsinoFormService.resetForm(this.editForm, instituicaoEnsino);

    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      instituicaoEnsino.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(
        map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.instituicaoEnsino?.cidade)),
      )
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
