import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-empresa.test-samples';

import { TarefaEmpresaFormService } from './tarefa-empresa-form.service';

describe('TarefaEmpresa Form Service', () => {
  let service: TarefaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataHora: expect.any(Object),
            empresa: expect.any(Object),
            contador: expect.any(Object),
            tarefa: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataHora: expect.any(Object),
            empresa: expect.any(Object),
            contador: expect.any(Object),
            tarefa: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaEmpresa', () => {
      it('should return NewTarefaEmpresa for default TarefaEmpresa initial value', () => {
        const formGroup = service.createTarefaEmpresaFormGroup(sampleWithNewData);

        const tarefaEmpresa = service.getTarefaEmpresa(formGroup) as any;

        expect(tarefaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaEmpresa for empty TarefaEmpresa initial value', () => {
        const formGroup = service.createTarefaEmpresaFormGroup();

        const tarefaEmpresa = service.getTarefaEmpresa(formGroup) as any;

        expect(tarefaEmpresa).toMatchObject({});
      });

      it('should return ITarefaEmpresa', () => {
        const formGroup = service.createTarefaEmpresaFormGroup(sampleWithRequiredData);

        const tarefaEmpresa = service.getTarefaEmpresa(formGroup) as any;

        expect(tarefaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createTarefaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaEmpresa should disable id FormControl', () => {
        const formGroup = service.createTarefaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
