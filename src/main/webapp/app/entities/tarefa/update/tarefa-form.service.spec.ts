import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa.test-samples';

import { TarefaFormService } from './tarefa-form.service';

describe('Tarefa Form Service', () => {
  let service: TarefaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            numeroDias: expect.any(Object),
            diaUtil: expect.any(Object),
            valor: expect.any(Object),
            notificarCliente: expect.any(Object),
            geraMulta: expect.any(Object),
            exibirEmpresa: expect.any(Object),
            dataLegal: expect.any(Object),
            pontos: expect.any(Object),
            tipoTarefa: expect.any(Object),
            esfera: expect.any(Object),
            frequencia: expect.any(Object),
            competencia: expect.any(Object),
          }),
        );
      });

      it('passing ITarefa should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            numeroDias: expect.any(Object),
            diaUtil: expect.any(Object),
            valor: expect.any(Object),
            notificarCliente: expect.any(Object),
            geraMulta: expect.any(Object),
            exibirEmpresa: expect.any(Object),
            dataLegal: expect.any(Object),
            pontos: expect.any(Object),
            tipoTarefa: expect.any(Object),
            esfera: expect.any(Object),
            frequencia: expect.any(Object),
            competencia: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefa', () => {
      it('should return NewTarefa for default Tarefa initial value', () => {
        const formGroup = service.createTarefaFormGroup(sampleWithNewData);

        const tarefa = service.getTarefa(formGroup) as any;

        expect(tarefa).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefa for empty Tarefa initial value', () => {
        const formGroup = service.createTarefaFormGroup();

        const tarefa = service.getTarefa(formGroup) as any;

        expect(tarefa).toMatchObject({});
      });

      it('should return ITarefa', () => {
        const formGroup = service.createTarefaFormGroup(sampleWithRequiredData);

        const tarefa = service.getTarefa(formGroup) as any;

        expect(tarefa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefa should not enable id FormControl', () => {
        const formGroup = service.createTarefaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefa should disable id FormControl', () => {
        const formGroup = service.createTarefaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
