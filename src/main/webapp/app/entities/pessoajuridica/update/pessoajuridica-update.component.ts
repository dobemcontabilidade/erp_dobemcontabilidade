import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoajuridica } from '../pessoajuridica.model';
import { PessoajuridicaService } from '../service/pessoajuridica.service';
import { PessoajuridicaFormService, PessoajuridicaFormGroup } from './pessoajuridica-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pessoajuridica-update',
  templateUrl: './pessoajuridica-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PessoajuridicaUpdateComponent implements OnInit {
  isSaving = false;
  pessoajuridica: IPessoajuridica | null = null;

  protected pessoajuridicaService = inject(PessoajuridicaService);
  protected pessoajuridicaFormService = inject(PessoajuridicaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PessoajuridicaFormGroup = this.pessoajuridicaFormService.createPessoajuridicaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoajuridica }) => {
      this.pessoajuridica = pessoajuridica;
      if (pessoajuridica) {
        this.updateForm(pessoajuridica);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoajuridica = this.pessoajuridicaFormService.getPessoajuridica(this.editForm);
    if (pessoajuridica.id !== null) {
      this.subscribeToSaveResponse(this.pessoajuridicaService.update(pessoajuridica));
    } else {
      this.subscribeToSaveResponse(this.pessoajuridicaService.create(pessoajuridica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoajuridica>>): void {
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

  protected updateForm(pessoajuridica: IPessoajuridica): void {
    this.pessoajuridica = pessoajuridica;
    this.pessoajuridicaFormService.resetForm(this.editForm, pessoajuridica);
  }
}
