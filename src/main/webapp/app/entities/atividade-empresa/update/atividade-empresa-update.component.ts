import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IAtividadeEmpresa } from '../atividade-empresa.model';
import { AtividadeEmpresaService } from '../service/atividade-empresa.service';
import { AtividadeEmpresaFormService, AtividadeEmpresaFormGroup } from './atividade-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-atividade-empresa-update',
  templateUrl: './atividade-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AtividadeEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  atividadeEmpresa: IAtividadeEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];

  protected atividadeEmpresaService = inject(AtividadeEmpresaService);
  protected atividadeEmpresaFormService = inject(AtividadeEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AtividadeEmpresaFormGroup = this.atividadeEmpresaFormService.createAtividadeEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ atividadeEmpresa }) => {
      this.atividadeEmpresa = atividadeEmpresa;
      if (atividadeEmpresa) {
        this.updateForm(atividadeEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const atividadeEmpresa = this.atividadeEmpresaFormService.getAtividadeEmpresa(this.editForm);
    if (atividadeEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.atividadeEmpresaService.update(atividadeEmpresa));
    } else {
      this.subscribeToSaveResponse(this.atividadeEmpresaService.create(atividadeEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtividadeEmpresa>>): void {
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

  protected updateForm(atividadeEmpresa: IAtividadeEmpresa): void {
    this.atividadeEmpresa = atividadeEmpresa;
    this.atividadeEmpresaFormService.resetForm(this.editForm, atividadeEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      atividadeEmpresa.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.atividadeEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
