import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-empresa-modelo.test-samples';

import { TarefaEmpresaModeloFormService } from './tarefa-empresa-modelo-form.service';

describe('TarefaEmpresaModelo Form Service', () => {
  let service: TarefaEmpresaModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaEmpresaModeloFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaEmpresaModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdmin: expect.any(Object),
            dataLegal: expect.any(Object),
            empresaModelo: expect.any(Object),
            servicoContabil: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaEmpresaModelo should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdmin: expect.any(Object),
            dataLegal: expect.any(Object),
            empresaModelo: expect.any(Object),
            servicoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaEmpresaModelo', () => {
      it('should return NewTarefaEmpresaModelo for default TarefaEmpresaModelo initial value', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup(sampleWithNewData);

        const tarefaEmpresaModelo = service.getTarefaEmpresaModelo(formGroup) as any;

        expect(tarefaEmpresaModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaEmpresaModelo for empty TarefaEmpresaModelo initial value', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup();

        const tarefaEmpresaModelo = service.getTarefaEmpresaModelo(formGroup) as any;

        expect(tarefaEmpresaModelo).toMatchObject({});
      });

      it('should return ITarefaEmpresaModelo', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup(sampleWithRequiredData);

        const tarefaEmpresaModelo = service.getTarefaEmpresaModelo(formGroup) as any;

        expect(tarefaEmpresaModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaEmpresaModelo should not enable id FormControl', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaEmpresaModelo should disable id FormControl', () => {
        const formGroup = service.createTarefaEmpresaModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
