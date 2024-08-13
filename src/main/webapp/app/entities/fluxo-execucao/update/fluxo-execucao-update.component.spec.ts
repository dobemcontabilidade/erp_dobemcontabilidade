import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { FluxoExecucaoService } from '../service/fluxo-execucao.service';
import { IFluxoExecucao } from '../fluxo-execucao.model';
import { FluxoExecucaoFormService } from './fluxo-execucao-form.service';

import { FluxoExecucaoUpdateComponent } from './fluxo-execucao-update.component';

describe('FluxoExecucao Management Update Component', () => {
  let comp: FluxoExecucaoUpdateComponent;
  let fixture: ComponentFixture<FluxoExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fluxoExecucaoFormService: FluxoExecucaoFormService;
  let fluxoExecucaoService: FluxoExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FluxoExecucaoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FluxoExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FluxoExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fluxoExecucaoFormService = TestBed.inject(FluxoExecucaoFormService);
    fluxoExecucaoService = TestBed.inject(FluxoExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fluxoExecucao: IFluxoExecucao = { id: 456 };

      activatedRoute.data = of({ fluxoExecucao });
      comp.ngOnInit();

      expect(comp.fluxoExecucao).toEqual(fluxoExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoExecucao>>();
      const fluxoExecucao = { id: 123 };
      jest.spyOn(fluxoExecucaoFormService, 'getFluxoExecucao').mockReturnValue(fluxoExecucao);
      jest.spyOn(fluxoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fluxoExecucao }));
      saveSubject.complete();

      // THEN
      expect(fluxoExecucaoFormService.getFluxoExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fluxoExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(fluxoExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoExecucao>>();
      const fluxoExecucao = { id: 123 };
      jest.spyOn(fluxoExecucaoFormService, 'getFluxoExecucao').mockReturnValue({ id: null });
      jest.spyOn(fluxoExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fluxoExecucao }));
      saveSubject.complete();

      // THEN
      expect(fluxoExecucaoFormService.getFluxoExecucao).toHaveBeenCalled();
      expect(fluxoExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFluxoExecucao>>();
      const fluxoExecucao = { id: 123 };
      jest.spyOn(fluxoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fluxoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fluxoExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
