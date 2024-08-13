import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAreaContabil } from '../area-contabil.model';
import { AreaContabilService } from '../service/area-contabil.service';
import { AreaContabilFormService, AreaContabilFormGroup } from './area-contabil-form.service';

@Component({
  standalone: true,
  selector: 'jhi-area-contabil-update',
  templateUrl: './area-contabil-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AreaContabilUpdateComponent implements OnInit {
  isSaving = false;
  areaContabil: IAreaContabil | null = null;

  protected areaContabilService = inject(AreaContabilService);
  protected areaContabilFormService = inject(AreaContabilFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AreaContabilFormGroup = this.areaContabilFormService.createAreaContabilFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaContabil }) => {
      this.areaContabil = areaContabil;
      if (areaContabil) {
        this.updateForm(areaContabil);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const areaContabil = this.areaContabilFormService.getAreaContabil(this.editForm);
    if (areaContabil.id !== null) {
      this.subscribeToSaveResponse(this.areaContabilService.update(areaContabil));
    } else {
      this.subscribeToSaveResponse(this.areaContabilService.create(areaContabil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaContabil>>): void {
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

  protected updateForm(areaContabil: IAreaContabil): void {
    this.areaContabil = areaContabil;
    this.areaContabilFormService.resetForm(this.editForm, areaContabil);
  }
}
