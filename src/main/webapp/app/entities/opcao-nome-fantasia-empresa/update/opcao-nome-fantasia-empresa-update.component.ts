import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';
import { OpcaoNomeFantasiaEmpresaService } from '../service/opcao-nome-fantasia-empresa.service';
import { OpcaoNomeFantasiaEmpresaFormService, OpcaoNomeFantasiaEmpresaFormGroup } from './opcao-nome-fantasia-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-opcao-nome-fantasia-empresa-update',
  templateUrl: './opcao-nome-fantasia-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OpcaoNomeFantasiaEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];

  protected opcaoNomeFantasiaEmpresaService = inject(OpcaoNomeFantasiaEmpresaService);
  protected opcaoNomeFantasiaEmpresaFormService = inject(OpcaoNomeFantasiaEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OpcaoNomeFantasiaEmpresaFormGroup = this.opcaoNomeFantasiaEmpresaFormService.createOpcaoNomeFantasiaEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opcaoNomeFantasiaEmpresa }) => {
      this.opcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresa;
      if (opcaoNomeFantasiaEmpresa) {
        this.updateForm(opcaoNomeFantasiaEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const opcaoNomeFantasiaEmpresa = this.opcaoNomeFantasiaEmpresaFormService.getOpcaoNomeFantasiaEmpresa(this.editForm);
    if (opcaoNomeFantasiaEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.opcaoNomeFantasiaEmpresaService.update(opcaoNomeFantasiaEmpresa));
    } else {
      this.subscribeToSaveResponse(this.opcaoNomeFantasiaEmpresaService.create(opcaoNomeFantasiaEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpcaoNomeFantasiaEmpresa>>): void {
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

  protected updateForm(opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa): void {
    this.opcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresa;
    this.opcaoNomeFantasiaEmpresaFormService.resetForm(this.editForm, opcaoNomeFantasiaEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      opcaoNomeFantasiaEmpresa.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.opcaoNomeFantasiaEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
