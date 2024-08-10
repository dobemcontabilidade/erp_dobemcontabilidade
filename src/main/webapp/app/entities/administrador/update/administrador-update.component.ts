import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IAdministrador } from '../administrador.model';
import { AdministradorService } from '../service/administrador.service';
import { AdministradorFormService, AdministradorFormGroup } from './administrador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-administrador-update',
  templateUrl: './administrador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AdministradorUpdateComponent implements OnInit {
  isSaving = false;
  administrador: IAdministrador | null = null;

  pessoasCollection: IPessoa[] = [];

  protected administradorService = inject(AdministradorService);
  protected administradorFormService = inject(AdministradorFormService);
  protected pessoaService = inject(PessoaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AdministradorFormGroup = this.administradorFormService.createAdministradorFormGroup();

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrador }) => {
      this.administrador = administrador;
      if (administrador) {
        this.updateForm(administrador);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administrador = this.administradorFormService.getAdministrador(this.editForm);
    if (administrador.id !== null) {
      this.subscribeToSaveResponse(this.administradorService.update(administrador));
    } else {
      this.subscribeToSaveResponse(this.administradorService.create(administrador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrador>>): void {
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

  protected updateForm(administrador: IAdministrador): void {
    this.administrador = administrador;
    this.administradorFormService.resetForm(this.editForm, administrador);

    this.pessoasCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(this.pessoasCollection, administrador.pessoa);
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaService
      .query({ 'administradorId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.administrador?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasCollection = pessoas));
  }
}
