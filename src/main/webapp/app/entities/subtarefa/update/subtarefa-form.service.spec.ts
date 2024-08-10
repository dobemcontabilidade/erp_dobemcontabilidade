import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../subtarefa.test-samples';

import { SubtarefaFormService } from './subtarefa-form.service';

describe('Subtarefa Form Service', () => {
  let service: SubtarefaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubtarefaFormService);
  });

  describe('Service methods', () => {
    describe('createSubtarefaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubtarefaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordem: expect.any(Object),
            item: expect.any(Object),
            descricao: expect.any(Object),
            tarefa: expect.any(Object),
          }),
        );
      });

      it('passing ISubtarefa should create a new form with FormGroup', () => {
        const formGroup = service.createSubtarefaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordem: expect.any(Object),
            item: expect.any(Object),
            descricao: expect.any(Object),
            tarefa: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubtarefa', () => {
      it('should return NewSubtarefa for default Subtarefa initial value', () => {
        const formGroup = service.createSubtarefaFormGroup(sampleWithNewData);

        const subtarefa = service.getSubtarefa(formGroup) as any;

        expect(subtarefa).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubtarefa for empty Subtarefa initial value', () => {
        const formGroup = service.createSubtarefaFormGroup();

        const subtarefa = service.getSubtarefa(formGroup) as any;

        expect(subtarefa).toMatchObject({});
      });

      it('should return ISubtarefa', () => {
        const formGroup = service.createSubtarefaFormGroup(sampleWithRequiredData);

        const subtarefa = service.getSubtarefa(formGroup) as any;

        expect(subtarefa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubtarefa should not enable id FormControl', () => {
        const formGroup = service.createSubtarefaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubtarefa should disable id FormControl', () => {
        const formGroup = service.createSubtarefaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
