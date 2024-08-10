import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';
import { OpcaoRazaoSocialEmpresaService } from '../service/opcao-razao-social-empresa.service';
import { OpcaoRazaoSocialEmpresaFormService, OpcaoRazaoSocialEmpresaFormGroup } from './opcao-razao-social-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-opcao-razao-social-empresa-update',
  templateUrl: './opcao-razao-social-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OpcaoRazaoSocialEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa | null = null;

  empresasSharedCollection: IEmpresa[] = [];

  protected opcaoRazaoSocialEmpresaService = inject(OpcaoRazaoSocialEmpresaService);
  protected opcaoRazaoSocialEmpresaFormService = inject(OpcaoRazaoSocialEmpresaFormService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OpcaoRazaoSocialEmpresaFormGroup = this.opcaoRazaoSocialEmpresaFormService.createOpcaoRazaoSocialEmpresaFormGroup();

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opcaoRazaoSocialEmpresa }) => {
      this.opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresa;
      if (opcaoRazaoSocialEmpresa) {
        this.updateForm(opcaoRazaoSocialEmpresa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const opcaoRazaoSocialEmpresa = this.opcaoRazaoSocialEmpresaFormService.getOpcaoRazaoSocialEmpresa(this.editForm);
    if (opcaoRazaoSocialEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.opcaoRazaoSocialEmpresaService.update(opcaoRazaoSocialEmpresa));
    } else {
      this.subscribeToSaveResponse(this.opcaoRazaoSocialEmpresaService.create(opcaoRazaoSocialEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpcaoRazaoSocialEmpresa>>): void {
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

  protected updateForm(opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa): void {
    this.opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresa;
    this.opcaoRazaoSocialEmpresaFormService.resetForm(this.editForm, opcaoRazaoSocialEmpresa);

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      opcaoRazaoSocialEmpresa.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) =>
          this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.opcaoRazaoSocialEmpresa?.empresa),
        ),
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
