import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { FuncaoSocioEnum } from 'app/entities/enumerations/funcao-socio-enum.model';
import { SocioService } from '../service/socio.service';
import { ISocio } from '../socio.model';
import { SocioFormService, SocioFormGroup } from './socio-form.service';

@Component({
  standalone: true,
  selector: 'jhi-socio-update',
  templateUrl: './socio-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SocioUpdateComponent implements OnInit {
  isSaving = false;
  socio: ISocio | null = null;
  funcaoSocioEnumValues = Object.keys(FuncaoSocioEnum);

  pessoaFisicasCollection: IPessoaFisica[] = [];
  empresasSharedCollection: IEmpresa[] = [];

  protected socioService = inject(SocioService);
  protected socioFormService = inject(SocioFormService);
  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected empresaService = inject(EmpresaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SocioFormGroup = this.socioFormService.createSocioFormGroup();

  comparePessoaFisica = (o1: IPessoaFisica | null, o2: IPessoaFisica | null): boolean =>
    this.pessoaFisicaService.comparePessoaFisica(o1, o2);

  compareEmpresa = (o1: IEmpresa | null, o2: IEmpresa | null): boolean => this.empresaService.compareEmpresa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socio }) => {
      this.socio = socio;
      if (socio) {
        this.updateForm(socio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const socio = this.socioFormService.getSocio(this.editForm);
    if (socio.id !== null) {
      this.subscribeToSaveResponse(this.socioService.update(socio));
    } else {
      this.subscribeToSaveResponse(this.socioService.create(socio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocio>>): void {
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

  protected updateForm(socio: ISocio): void {
    this.socio = socio;
    this.socioFormService.resetForm(this.editForm, socio);

    this.pessoaFisicasCollection = this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(
      this.pessoaFisicasCollection,
      socio.pessoaFisica,
    );
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(
      this.empresasSharedCollection,
      socio.empresa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaFisicaService
      .query({ filter: 'socio-is-null' })
      .pipe(map((res: HttpResponse<IPessoaFisica[]>) => res.body ?? []))
      .pipe(
        map((pessoaFisicas: IPessoaFisica[]) =>
          this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(pessoaFisicas, this.socio?.pessoaFisica),
        ),
      )
      .subscribe((pessoaFisicas: IPessoaFisica[]) => (this.pessoaFisicasCollection = pessoaFisicas));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing<IEmpresa>(empresas, this.socio?.empresa)))
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }
}
