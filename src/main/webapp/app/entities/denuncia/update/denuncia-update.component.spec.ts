import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { DenunciaService } from '../service/denuncia.service';
import { IDenuncia } from '../denuncia.model';
import { DenunciaFormService } from './denuncia-form.service';

import { DenunciaUpdateComponent } from './denuncia-update.component';

describe('Denuncia Management Update Component', () => {
  let comp: DenunciaUpdateComponent;
  let fixture: ComponentFixture<DenunciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let denunciaFormService: DenunciaFormService;
  let denunciaService: DenunciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DenunciaUpdateComponent],
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
      .overrideTemplate(DenunciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DenunciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    denunciaFormService = TestBed.inject(DenunciaFormService);
    denunciaService = TestBed.inject(DenunciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const denuncia: IDenuncia = { id: 456 };

      activatedRoute.data = of({ denuncia });
      comp.ngOnInit();

      expect(comp.denuncia).toEqual(denuncia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDenuncia>>();
      const denuncia = { id: 123 };
      jest.spyOn(denunciaFormService, 'getDenuncia').mockReturnValue(denuncia);
      jest.spyOn(denunciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ denuncia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: denuncia }));
      saveSubject.complete();

      // THEN
      expect(denunciaFormService.getDenuncia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(denunciaService.update).toHaveBeenCalledWith(expect.objectContaining(denuncia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDenuncia>>();
      const denuncia = { id: 123 };
      jest.spyOn(denunciaFormService, 'getDenuncia').mockReturnValue({ id: null });
      jest.spyOn(denunciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ denuncia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: denuncia }));
      saveSubject.complete();

      // THEN
      expect(denunciaFormService.getDenuncia).toHaveBeenCalled();
      expect(denunciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDenuncia>>();
      const denuncia = { id: 123 };
      jest.spyOn(denunciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ denuncia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(denunciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
