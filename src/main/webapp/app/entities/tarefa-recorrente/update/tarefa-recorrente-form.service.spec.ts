import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-recorrente.test-samples';

import { TarefaRecorrenteFormService } from './tarefa-recorrente-form.service';

describe('TarefaRecorrente Form Service', () => {
  let service: TarefaRecorrenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaRecorrenteFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaRecorrenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            notificarCliente: expect.any(Object),
            notificarContador: expect.any(Object),
            anoReferencia: expect.any(Object),
            dataAdmin: expect.any(Object),
            recorencia: expect.any(Object),
            servicoContabilAssinaturaEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaRecorrente should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            notificarCliente: expect.any(Object),
            notificarContador: expect.any(Object),
            anoReferencia: expect.any(Object),
            dataAdmin: expect.any(Object),
            recorencia: expect.any(Object),
            servicoContabilAssinaturaEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaRecorrente', () => {
      it('should return NewTarefaRecorrente for default TarefaRecorrente initial value', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup(sampleWithNewData);

        const tarefaRecorrente = service.getTarefaRecorrente(formGroup) as any;

        expect(tarefaRecorrente).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaRecorrente for empty TarefaRecorrente initial value', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup();

        const tarefaRecorrente = service.getTarefaRecorrente(formGroup) as any;

        expect(tarefaRecorrente).toMatchObject({});
      });

      it('should return ITarefaRecorrente', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup(sampleWithRequiredData);

        const tarefaRecorrente = service.getTarefaRecorrente(formGroup) as any;

        expect(tarefaRecorrente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaRecorrente should not enable id FormControl', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaRecorrente should disable id FormControl', () => {
        const formGroup = service.createTarefaRecorrenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
