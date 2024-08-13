import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SocioDetailComponent } from './socio-detail.component';

describe('Socio Management Detail Component', () => {
  let comp: SocioDetailComponent;
  let fixture: ComponentFixture<SocioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SocioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SocioDetailComponent,
              resolve: { socio: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SocioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load socio on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SocioDetailComponent);

      // THEN
      expect(instance.socio()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
