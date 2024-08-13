import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-recorrente-empresa-modelo.test-samples';

import { TarefaRecorrenteEmpresaModeloFormService } from './tarefa-recorrente-empresa-modelo-form.service';

describe('TarefaRecorrenteEmpresaModelo Form Service', () => {
  let service: TarefaRecorrenteEmpresaModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaRecorrenteEmpresaModeloFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaRecorrenteEmpresaModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaAdmin: expect.any(Object),
            mesLegal: expect.any(Object),
            recorrencia: expect.any(Object),
            servicoContabilEmpresaModelo: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaRecorrenteEmpresaModelo should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaAdmin: expect.any(Object),
            mesLegal: expect.any(Object),
            recorrencia: expect.any(Object),
            servicoContabilEmpresaModelo: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaRecorrenteEmpresaModelo', () => {
      it('should return NewTarefaRecorrenteEmpresaModelo for default TarefaRecorrenteEmpresaModelo initial value', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup(sampleWithNewData);

        const tarefaRecorrenteEmpresaModelo = service.getTarefaRecorrenteEmpresaModelo(formGroup) as any;

        expect(tarefaRecorrenteEmpresaModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaRecorrenteEmpresaModelo for empty TarefaRecorrenteEmpresaModelo initial value', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup();

        const tarefaRecorrenteEmpresaModelo = service.getTarefaRecorrenteEmpresaModelo(formGroup) as any;

        expect(tarefaRecorrenteEmpresaModelo).toMatchObject({});
      });

      it('should return ITarefaRecorrenteEmpresaModelo', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup(sampleWithRequiredData);

        const tarefaRecorrenteEmpresaModelo = service.getTarefaRecorrenteEmpresaModelo(formGroup) as any;

        expect(tarefaRecorrenteEmpresaModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaRecorrenteEmpresaModelo should not enable id FormControl', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaRecorrenteEmpresaModelo should disable id FormControl', () => {
        const formGroup = service.createTarefaRecorrenteEmpresaModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
