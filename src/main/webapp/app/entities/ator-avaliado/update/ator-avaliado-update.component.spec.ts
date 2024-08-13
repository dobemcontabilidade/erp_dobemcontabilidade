import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AtorAvaliadoService } from '../service/ator-avaliado.service';
import { IAtorAvaliado } from '../ator-avaliado.model';
import { AtorAvaliadoFormService } from './ator-avaliado-form.service';

import { AtorAvaliadoUpdateComponent } from './ator-avaliado-update.component';

describe('AtorAvaliado Management Update Component', () => {
  let comp: AtorAvaliadoUpdateComponent;
  let fixture: ComponentFixture<AtorAvaliadoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let atorAvaliadoFormService: AtorAvaliadoFormService;
  let atorAvaliadoService: AtorAvaliadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AtorAvaliadoUpdateComponent],
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
      .overrideTemplate(AtorAvaliadoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtorAvaliadoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    atorAvaliadoFormService = TestBed.inject(AtorAvaliadoFormService);
    atorAvaliadoService = TestBed.inject(AtorAvaliadoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const atorAvaliado: IAtorAvaliado = { id: 456 };

      activatedRoute.data = of({ atorAvaliado });
      comp.ngOnInit();

      expect(comp.atorAvaliado).toEqual(atorAvaliado);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtorAvaliado>>();
      const atorAvaliado = { id: 123 };
      jest.spyOn(atorAvaliadoFormService, 'getAtorAvaliado').mockReturnValue(atorAvaliado);
      jest.spyOn(atorAvaliadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atorAvaliado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atorAvaliado }));
      saveSubject.complete();

      // THEN
      expect(atorAvaliadoFormService.getAtorAvaliado).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(atorAvaliadoService.update).toHaveBeenCalledWith(expect.objectContaining(atorAvaliado));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtorAvaliado>>();
      const atorAvaliado = { id: 123 };
      jest.spyOn(atorAvaliadoFormService, 'getAtorAvaliado').mockReturnValue({ id: null });
      jest.spyOn(atorAvaliadoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atorAvaliado: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atorAvaliado }));
      saveSubject.complete();

      // THEN
      expect(atorAvaliadoFormService.getAtorAvaliado).toHaveBeenCalled();
      expect(atorAvaliadoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtorAvaliado>>();
      const atorAvaliado = { id: 123 };
      jest.spyOn(atorAvaliadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atorAvaliado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(atorAvaliadoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
